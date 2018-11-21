import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './monster-type.reducer';
import { IMonsterType } from 'app/shared/model/monster-type.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMonsterTypeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IMonsterTypeUpdateState {
  isNew: boolean;
}

export class MonsterTypeUpdate extends React.Component<IMonsterTypeUpdateProps, IMonsterTypeUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { monsterTypeEntity } = this.props;
      const entity = {
        ...monsterTypeEntity,
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
    this.props.history.push('/entity/monster-type');
  };

  render() {
    const { monsterTypeEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="tabletTopRpgApp.monsterType.home.createOrEditLabel">Create or edit a MonsterType</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : monsterTypeEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="monster-type-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    Name
                  </Label>
                  <AvField id="monster-type-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="strengthLabel" for="strength">
                    Strength
                  </Label>
                  <AvField id="monster-type-strength" type="string" className="form-control" name="strength" />
                </AvGroup>
                <AvGroup>
                  <Label id="agilityLabel" for="agility">
                    Agility
                  </Label>
                  <AvField id="monster-type-agility" type="string" className="form-control" name="agility" />
                </AvGroup>
                <AvGroup>
                  <Label id="constituitionLabel" for="constituition">
                    Constituition
                  </Label>
                  <AvField id="monster-type-constituition" type="string" className="form-control" name="constituition" />
                </AvGroup>
                <AvGroup>
                  <Label id="intelligenceLabel" for="intelligence">
                    Intelligence
                  </Label>
                  <AvField id="monster-type-intelligence" type="string" className="form-control" name="intelligence" />
                </AvGroup>
                <AvGroup>
                  <Label id="willpowerLabel" for="willpower">
                    Willpower
                  </Label>
                  <AvField id="monster-type-willpower" type="string" className="form-control" name="willpower" />
                </AvGroup>
                <AvGroup>
                  <Label id="charismaLabel" for="charisma">
                    Charisma
                  </Label>
                  <AvField id="monster-type-charisma" type="string" className="form-control" name="charisma" />
                </AvGroup>
                <AvGroup>
                  <Label id="hitpointsLabel" for="hitpoints">
                    Hitpoints
                  </Label>
                  <AvField id="monster-type-hitpoints" type="string" className="form-control" name="hitpoints" />
                </AvGroup>
                <AvGroup>
                  <Label id="attackLabel" for="attack">
                    Attack
                  </Label>
                  <AvField id="monster-type-attack" type="text" name="attack" />
                </AvGroup>
                <AvGroup>
                  <Label id="defenseLabel" for="defense">
                    Defense
                  </Label>
                  <AvField id="monster-type-defense" type="string" className="form-control" name="defense" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/monster-type" replace color="info">
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
  monsterTypeEntity: storeState.monsterType.entity,
  loading: storeState.monsterType.loading,
  updating: storeState.monsterType.updating,
  updateSuccess: storeState.monsterType.updateSuccess
});

const mapDispatchToProps = {
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
)(MonsterTypeUpdate);
