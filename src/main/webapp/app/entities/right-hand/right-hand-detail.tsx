import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './right-hand.reducer';
import { IRightHand } from 'app/shared/model/right-hand.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRightHandDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class RightHandDetail extends React.Component<IRightHandDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { rightHandEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            RightHand [<b>{rightHandEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{rightHandEntity.name}</dd>
            <dt>
              <span id="effect">Effect</span>
            </dt>
            <dd>{rightHandEntity.effect}</dd>
            <dt>
              <span id="price">Price</span>
            </dt>
            <dd>{rightHandEntity.price}</dd>
            <dt>
              <span id="attack">Attack</span>
            </dt>
            <dd>{rightHandEntity.attack}</dd>
            <dt>
              <span id="defense">Defense</span>
            </dt>
            <dd>{rightHandEntity.defense}</dd>
            <dt>
              <span id="type">Type</span>
            </dt>
            <dd>{rightHandEntity.type}</dd>
            <dt>Shop</dt>
            <dd>
              {rightHandEntity.shops
                ? rightHandEntity.shops.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === rightHandEntity.shops.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/right-hand" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/right-hand/${rightHandEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ rightHand }: IRootState) => ({
  rightHandEntity: rightHand.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RightHandDetail);
