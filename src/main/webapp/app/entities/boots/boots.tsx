import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './boots.reducer';
import { IBoots } from 'app/shared/model/boots.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBootsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Boots extends React.Component<IBootsProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { bootsList, match } = this.props;
    return (
      <div>
        <h2 id="boots-heading">
          Boots
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Boots
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
              {bootsList.map((boots, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${boots.id}`} color="link" size="sm">
                      {boots.id}
                    </Button>
                  </td>
                  <td>{boots.name}</td>
                  <td>{boots.effect}</td>
                  <td>{boots.price}</td>
                  <td>{boots.defence}</td>
                  <td>{boots.part}</td>
                  <td>
                    {boots.shops
                      ? boots.shops.map((val, j) => (
                          <span key={j}>
                            <Link to={`shop/${val.id}`}>{val.id}</Link>
                            {j === boots.shops.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${boots.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${boots.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${boots.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ boots }: IRootState) => ({
  bootsList: boots.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Boots);
