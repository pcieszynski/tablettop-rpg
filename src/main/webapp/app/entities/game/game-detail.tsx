import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './game.reducer';
import { IGame } from 'app/shared/model/game.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGameDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class GameDetail extends React.Component<IGameDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { gameEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Game [<b>{gameEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="currentEvent">Current Event</span>
            </dt>
            <dd>{gameEntity.currentEvent}</dd>
            <dt>
              <span id="currentPlayer">Current Player</span>
            </dt>
            <dd>{gameEntity.currentPlayer}</dd>
            <dt>Player</dt>
            <dd>
              {gameEntity.players
                ? gameEntity.players.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === gameEntity.players.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
            <dt>Gamemaster</dt>
            <dd>{gameEntity.gamemasterId ? gameEntity.gamemasterId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/game" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/game/${gameEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ game }: IRootState) => ({
  gameEntity: game.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(GameDetail);
