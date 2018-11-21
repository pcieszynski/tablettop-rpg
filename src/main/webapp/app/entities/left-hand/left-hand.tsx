import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './left-hand.reducer';
import { ILeftHand } from 'app/shared/model/left-hand.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ILeftHandProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class LeftHand extends React.Component<ILeftHandProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { leftHandList, match } = this.props;
    return (
      <div>
        <h2 id="left-hand-heading">
          Left Hands
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Left Hand
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
                <th>Attack</th>
                <th>Defense</th>
                <th>Type</th>
                <th>Shop</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {leftHandList.map((leftHand, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${leftHand.id}`} color="link" size="sm">
                      {leftHand.id}
                    </Button>
                  </td>
                  <td>{leftHand.name}</td>
                  <td>{leftHand.effect}</td>
                  <td>{leftHand.price}</td>
                  <td>{leftHand.attack}</td>
                  <td>{leftHand.defense}</td>
                  <td>{leftHand.type}</td>
                  <td>
                    {leftHand.shops
                      ? leftHand.shops.map((val, j) => (
                          <span key={j}>
                            <Link to={`shop/${val.id}`}>{val.id}</Link>
                            {j === leftHand.shops.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${leftHand.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${leftHand.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${leftHand.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ leftHand }: IRootState) => ({
  leftHandList: leftHand.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(LeftHand);
