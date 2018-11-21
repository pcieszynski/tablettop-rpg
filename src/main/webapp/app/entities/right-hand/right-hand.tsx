import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './right-hand.reducer';
import { IRightHand } from 'app/shared/model/right-hand.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRightHandProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class RightHand extends React.Component<IRightHandProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { rightHandList, match } = this.props;
    return (
      <div>
        <h2 id="right-hand-heading">
          Right Hands
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Right Hand
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
              {rightHandList.map((rightHand, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${rightHand.id}`} color="link" size="sm">
                      {rightHand.id}
                    </Button>
                  </td>
                  <td>{rightHand.name}</td>
                  <td>{rightHand.effect}</td>
                  <td>{rightHand.price}</td>
                  <td>{rightHand.attack}</td>
                  <td>{rightHand.defense}</td>
                  <td>{rightHand.type}</td>
                  <td>
                    {rightHand.shops
                      ? rightHand.shops.map((val, j) => (
                          <span key={j}>
                            <Link to={`shop/${val.id}`}>{val.id}</Link>
                            {j === rightHand.shops.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${rightHand.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${rightHand.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${rightHand.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ rightHand }: IRootState) => ({
  rightHandList: rightHand.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RightHand);
