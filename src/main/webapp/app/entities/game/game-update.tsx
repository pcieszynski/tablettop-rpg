import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPlayer } from 'app/shared/model/player.model';
import { getEntities as getPlayers } from 'app/entities/player/player.reducer';
import { IGamemaster } from 'app/shared/model/gamemaster.model';
import { getEntities as getGamemasters } from 'app/entities/gamemaster/gamemaster.reducer';
import { ICharacter } from 'app/shared/model/character.model';
import { getEntities as getCharacters } from 'app/entities/character/character.reducer';
import { getEntity, updateEntity, createEntity, reset } from './game.reducer';
import { IGame } from 'app/shared/model/game.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IGameUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IGameUpdateState {
  isNew: boolean;
  idsplayer: any[];
  gamemasterId: string;
  characterId: string;
}

export class GameUpdate extends React.Component<IGameUpdateProps, IGameUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsplayer: [],
      gamemasterId: '0',
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

    this.props.getPlayers();
    this.props.getGamemasters();
    this.props.getCharacters();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { gameEntity } = this.props;
      const entity = {
        ...gameEntity,
        ...values,
        players: mapIdList(values.players)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/game');
  };

  render() {
    const { gameEntity, players, gamemasters, characters, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="tabletTopRpgApp.game.home.createOrEditLabel">Create or edit a Game</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : gameEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="game-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="currentEventLabel" for="currentEvent">
                    Current Event
                  </Label>
                  <AvField id="game-currentEvent" type="string" className="form-control" name="currentEvent" />
                </AvGroup>
                <AvGroup>
                  <Label id="currentPlayerLabel" for="currentPlayer">
                    Current Player
                  </Label>
                  <AvField id="game-currentPlayer" type="text" name="currentPlayer" />
                </AvGroup>
                <AvGroup>
                  <Label for="players">Player</Label>
                  <AvInput
                    id="game-player"
                    type="select"
                    multiple
                    className="form-control"
                    name="players"
                    value={gameEntity.players && gameEntity.players.map(e => e.id)}
                  >
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
                  <Label for="gamemaster.id">Gamemaster</Label>
                  <AvInput id="game-gamemaster" type="select" className="form-control" name="gamemasterId">
                    <option value="" key="0" />
                    {gamemasters
                      ? gamemasters.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/game" replace color="info">
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
  gamemasters: storeState.gamemaster.entities,
  characters: storeState.character.entities,
  gameEntity: storeState.game.entity,
  loading: storeState.game.loading,
  updating: storeState.game.updating,
  updateSuccess: storeState.game.updateSuccess
});

const mapDispatchToProps = {
  getPlayers,
  getGamemasters,
  getCharacters,
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
)(GameUpdate);
