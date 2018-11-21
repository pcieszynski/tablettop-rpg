import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './legs.reducer';
import { ILegs } from 'app/shared/model/legs.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ILegsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Legs extends React.Component<ILegsProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { legsList, match } = this.props;
    return (
      <div>
        <h2 id="legs-heading">
          Legs
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Legs
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Effect</th>
                <th>Price</th>
                <th>Defence</th>
                <th>Part</th>
                <th>Shop</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {legsList.map((legs, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${legs.id}`} color="link" size="sm">
                      {legs.id}
                    </Button>
                  </td>
                  <td>{legs.name}</td>
                  <td>{legs.effect}</td>
                  <td>{legs.price}</td>
                  <td>{legs.defence}</td>
                  <td>{legs.part}</td>
                  <td>
                    {legs.shops
                      ? legs.shops.map((val, j) => (
                          <span key={j}>
                            <Link to={`shop/${val.id}`}>{val.id}</Link>
                            {j === legs.shops.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${legs.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${legs.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${legs.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ legs }: IRootState) => ({
  legsList: legs.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Legs);
