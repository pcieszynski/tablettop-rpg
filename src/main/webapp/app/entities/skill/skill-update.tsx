import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IProfession } from 'app/shared/model/profession.model';
import { getEntities as getProfessions } from 'app/entities/profession/profession.reducer';
import { ICharacter } from 'app/shared/model/character.model';
import { getEntities as getCharacters } from 'app/entities/character/character.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './skill.reducer';
import { ISkill } from 'app/shared/model/skill.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISkillUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ISkillUpdateState {
  isNew: boolean;
  professionId: string;
  characterId: string;
}

export class SkillUpdate extends React.Component<ISkillUpdateProps, ISkillUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      professionId: '0',
      characterId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getProfessions();
    this.props.getCharacters();
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { skillEntity } = this.props;
      const entity = {
        ...skillEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/skill');
  };

  render() {
    const { skillEntity, professions, characters, loading, updating } = this.props;
    const { isNew } = this.state;

    const { description } = skillEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="tabletTopRpgApp.skill.home.createOrEditLabel">Create or edit a Skill</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : skillEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="skill-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    Name
                  </Label>
                  <AvField id="skill-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="description">
                    Description
                  </Label>
                  <AvInput id="skill-description" type="textarea" name="description" />
                </AvGroup>
                <AvGroup>
                  <Label id="damageLabel" for="damage">
                    Damage
                  </Label>
                  <AvField id="skill-damage" type="text" name="damage" />
                </AvGroup>
                <AvGroup>
                  <Label id="healLabel" for="heal">
                    Heal
                  </Label>
                  <AvField id="skill-heal" type="string" className="form-control" name="heal" />
                </AvGroup>
                <AvGroup>
                  <Label id="levelLabel" for="level">
                    Level
                  </Label>
                  <AvField id="skill-level" type="string" className="form-control" name="level" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/skill" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  professions: storeState.profession.entities,
  characters: storeState.character.entities,
  skillEntity: storeState.skill.entity,
  loading: storeState.skill.loading,
  updating: storeState.skill.updating,
  updateSuccess: storeState.skill.updateSuccess
});

const mapDispatchToProps = {
  getProfessions,
  getCharacters,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(SkillUpdate);
