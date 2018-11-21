import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IMonster } from 'app/shared/model/monster.model';
import { getEntities as getMonsters } from 'app/entities/monster/monster.reducer';
import { IEvent } from 'app/shared/model/event.model';
import { getEntities as getEvents } from 'app/entities/event/event.reducer';
import { getEntity, updateEntity, createEntity, reset } from './battle.reducer';
import { IBattle } from 'app/shared/model/battle.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBattleUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IBattleUpdateState {
  isNew: boolean;
  idsmonster: any[];
  eventId: string;
}

export class BattleUpdate extends React.Component<IBattleUpdateProps, IBattleUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsmonster: [],
      eventId: '0',
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

    this.props.getMonsters();
    this.props.getEvents();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { battleEntity } = this.props;
      const entity = {
        ...battleEntity,
        ...values,
        monsters: mapIdList(values.monsters)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/battle');
  };

  render() {
    const { battleEntity, monsters, events, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="tabletTopRpgApp.battle.home.createOrEditLabel">Create or edit a Battle</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : battleEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="battle-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="monsterLabel" for="monster">
                    Monster
                  </Label>
                  <AvField id="battle-monster" type="text" name="monster" />
                </AvGroup>
                <AvGroup>
                  <Label id="monsterNumberLabel" for="monsterNumber">
                    Monster Number
                  </Label>
                  <AvField id="battle-monsterNumber" type="string" className="form-control" name="monsterNumber" />
                </AvGroup>
                <AvGroup>
                  <Label for="monsters">Monster</Label>
                  <AvInput
                    id="battle-monster"
                    type="select"
                    multiple
                    className="form-control"
                    name="monsters"
                    value={battleEntity.monsters && battleEntity.monsters.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {monsters
                      ? monsters.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="event.id">Event</Label>
                  <AvInput id="battle-event" type="select" className="form-control" name="eventId">
                    <option value="" key="0" />
                    {events
                      ? events.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/battle" replace color="info">
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
  monsters: storeState.monster.entities,
  events: storeState.event.entities,
  battleEntity: storeState.battle.entity,
  loading: storeState.battle.loading,
  updating: storeState.battle.updating,
  updateSuccess: storeState.battle.updateSuccess
});

const mapDispatchToProps = {
  getMonsters,
  getEvents,
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
)(BattleUpdate);
