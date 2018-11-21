import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './armour.reducer';
import { IArmour } from 'app/shared/model/armour.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IArmourProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Armour extends React.Component<IArmourProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { armourList, match } = this.props;
    return (
      <div>
        <h2 id="armour-heading">
          Armours
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Armour
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
              {armourList.map((armour, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${armour.id}`} color="link" size="sm">
                      {armour.id}
                    </Button>
                  </td>
                  <td>{armour.name}</td>
                  <td>{armour.effect}</td>
                  <td>{armour.price}</td>
                  <td>{armour.defence}</td>
                  <td>{armour.part}</td>
                  <td>
                    {armour.shops
                      ? armour.shops.map((val, j) => (
                          <span key={j}>
                            <Link to={`shop/${val.id}`}>{val.id}</Link>
                            {j === armour.shops.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${armour.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${armour.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${armour.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ armour }: IRootState) => ({
  armourList: armour.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Armour);
