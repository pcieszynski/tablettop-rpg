import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { byteSize, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './player-message.reducer';
import { IPlayerMessage } from 'app/shared/model/player-message.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPlayerMessageProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class PlayerMessage extends React.Component<IPlayerMessageProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { playerMessageList, match } = this.props;
    return (
      <div>
        <h2 id="player-message-heading">
          Player Messages
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Player Message
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Message</th>
                <th>Attack</th>
                <th>Heal</th>
                <th>Difficulty</th>
                <th>Success</th>
                <th>Attribute</th>
                <th>Player</th>
                <th>Event</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {playerMessageList.map((playerMessage, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${playerMessage.id}`} color="link" size="sm">
                      {playerMessage.id}
                    </Button>
                  </td>
                  <td>{playerMessage.message}</td>
                  <td>{playerMessage.attack}</td>
                  <td>{playerMessage.heal}</td>
                  <td>{playerMessage.difficulty}</td>
                  <td>{playerMessage.success ? 'true' : 'false'}</td>
                  <td>{playerMessage.attribute}</td>
                  <td>{playerMessage.playerId ? <Link to={`player/${playerMessage.playerId}`}>{playerMessage.playerId}</Link> : ''}</td>
                  <td>{playerMessage.eventId ? <Link to={`event/${playerMessage.eventId}`}>{playerMessage.eventId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${playerMessage.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${playerMessage.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${playerMessage.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ playerMessage }: IRootState) => ({
  playerMessageList: playerMessage.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PlayerMessage);
