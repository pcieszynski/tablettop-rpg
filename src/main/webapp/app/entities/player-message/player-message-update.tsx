import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPlayer } from 'app/shared/model/player.model';
import { getEntities as getPlayers } from 'app/entities/player/player.reducer';
import { IEvent } from 'app/shared/model/event.model';
import { getEntities as getEvents } from 'app/entities/event/event.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './player-message.reducer';
import { IPlayerMessage } from 'app/shared/model/player-message.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPlayerMessageUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IPlayerMessageUpdateState {
  isNew: boolean;
  playerId: string;
  eventId: string;
}

export class PlayerMessageUpdate extends React.Component<IPlayerMessageUpdateProps, IPlayerMessageUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      playerId: '0',
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

    this.props.getPlayers();
    this.props.getEvents();
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { playerMessageEntity } = this.props;
      const entity = {
        ...playerMessageEntity,
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
    this.props.history.push('/entity/player-message');
  };

  render() {
    const { playerMessageEntity, players, events, loading, updating } = this.props;
    const { isNew } = this.state;

    const { message } = playerMessageEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="tabletTopRpgApp.playerMessage.home.createOrEditLabel">Create or edit a PlayerMessage</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : playerMessageEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="player-message-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="messageLabel" for="message">
                    Message
                  </Label>
                  <AvInput id="player-message-message" type="textarea" name="message" />
                </AvGroup>
                <AvGroup>
                  <Label id="attackLabel" for="attack">
                    Attack
                  </Label>
                  <AvField id="player-message-attack" type="text" name="attack" />
                </AvGroup>
                <AvGroup>
                  <Label id="healLabel" for="heal">
                    Heal
                  </Label>
                  <AvField id="player-message-heal" type="string" className="form-control" name="heal" />
                </AvGroup>
                <AvGroup>
                  <Label id="difficultyLabel" for="difficulty">
                    Difficulty
                  </Label>
                  <AvField id="player-message-difficulty" type="string" className="form-control" name="difficulty" />
                </AvGroup>
                <AvGroup>
                  <Label id="successLabel" check>
                    <AvInput id="player-message-success" type="checkbox" className="form-control" name="success" />
                    Success
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="attributeLabel" for="attribute">
                    Attribute
                  </Label>
                  <AvField id="player-message-attribute" type="text" name="attribute" />
                </AvGroup>
                <AvGroup>
                  <Label for="player.id">Player</Label>
                  <AvInput id="player-message-player" type="select" className="form-control" name="playerId">
                    <option value="" key="0" />
                    {players
                      ? players.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="event.id">Event</Label>
                  <AvInput id="player-message-event" type="select" className="form-control" name="eventId">
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
                <Button tag={Link} id="cancel-save" to="/entity/player-message" replace color="info">
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
  players: storeState.player.entities,
  events: storeState.event.entities,
  playerMessageEntity: storeState.playerMessage.entity,
  loading: storeState.playerMessage.loading,
  updating: storeState.playerMessage.updating,
  updateSuccess: storeState.playerMessage.updateSuccess
});

const mapDispatchToProps = {
  getPlayers,
  getEvents,
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
)(PlayerMessageUpdate);
