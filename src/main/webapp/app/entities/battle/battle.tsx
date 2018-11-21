import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './battle.reducer';
import { IBattle } from 'app/shared/model/battle.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBattleProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Battle extends React.Component<IBattleProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { battleList, match } = this.props;
    return (
      <div>
        <h2 id="battle-heading">
          Battles
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Battle
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Monster</th>
                <th>Monster Number</th>
                <th>Monster</th>
                <th>Event</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {battleList.map((battle, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${battle.id}`} color="link" size="sm">
                      {battle.id}
                    </Button>
                  </td>
                  <td>{battle.monster}</td>
                  <td>{battle.monsterNumber}</td>
                  <td>
                    {battle.monsters
                      ? battle.monsters.map((val, j) => (
                          <span key={j}>
                            <Link to={`monster/${val.id}`}>{val.id}</Link>
                            {j === battle.monsters.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>{battle.eventId ? <Link to={`event/${battle.eventId}`}>{battle.eventId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${battle.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${battle.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${battle.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ battle }: IRootState) => ({
  battleList: battle.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Battle);
