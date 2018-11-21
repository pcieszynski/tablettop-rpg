import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ISkill } from 'app/shared/model/skill.model';
import { getEntities as getSkills } from 'app/entities/skill/skill.reducer';
import { getEntity, updateEntity, createEntity, reset } from './profession.reducer';
import { IProfession } from 'app/shared/model/profession.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProfessionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IProfessionUpdateState {
  isNew: boolean;
  idsskill: any[];
}

export class ProfessionUpdate extends React.Component<IProfessionUpdateProps, IProfessionUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsskill: [],
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

    this.props.getSkills();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { professionEntity } = this.props;
      const entity = {
        ...professionEntity,
        ...values,
        skills: mapIdList(values.skills)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/profession');
  };

  render() {
    const { professionEntity, skills, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="tabletTopRpgApp.profession.home.createOrEditLabel">Create or edit a Profession</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : professionEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="profession-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    Name
                  </Label>
                  <AvField id="profession-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="strengthModifierLabel" for="strengthModifier">
                    Strength Modifier
                  </Label>
                  <AvField id="profession-strengthModifier" type="string" className="form-control" name="strengthModifier" />
                </AvGroup>
                <AvGroup>
                  <Label id="agilityModifierLabel" for="agilityModifier">
                    Agility Modifier
                  </Label>
                  <AvField id="profession-agilityModifier" type="string" className="form-control" name="agilityModifier" />
                </AvGroup>
                <AvGroup>
                  <Label id="constituitionModifierLabel" for="constituitionModifier">
                    Constituition Modifier
                  </Label>
                  <AvField id="profession-constituitionModifier" type="string" className="form-control" name="constituitionModifier" />
                </AvGroup>
                <AvGroup>
                  <Label id="intelligenceModifierLabel" for="intelligenceModifier">
                    Intelligence Modifier
                  </Label>
                  <AvField id="profession-intelligenceModifier" type="string" className="form-control" name="intelligenceModifier" />
                </AvGroup>
                <AvGroup>
                  <Label id="willpowerModifierLabel" for="willpowerModifier">
                    Willpower Modifier
                  </Label>
                  <AvField id="profession-willpowerModifier" type="string" className="form-control" name="willpowerModifier" />
                </AvGroup>
                <AvGroup>
                  <Label id="charismaModifierLabel" for="charismaModifier">
                    Charisma Modifier
                  </Label>
                  <AvField id="profession-charismaModifier" type="string" className="form-control" name="charismaModifier" />
                </AvGroup>
                <AvGroup>
                  <Label for="skills">Skill</Label>
                  <AvInput
                    id="profession-skill"
                    type="select"
                    multiple
                    className="form-control"
                    name="skills"
                    value={professionEntity.skills && professionEntity.skills.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {skills
                      ? skills.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/profession" replace color="info">
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
  skills: storeState.skill.entities,
  professionEntity: storeState.profession.entity,
  loading: storeState.profession.loading,
  updating: storeState.profession.updating,
  updateSuccess: storeState.profession.updateSuccess
});

const mapDispatchToProps = {
  getSkills,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProfessionUpdate);
