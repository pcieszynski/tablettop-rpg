import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './left-hand.reducer';
import { ILeftHand } from 'app/shared/model/left-hand.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ILeftHandDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class LeftHandDetail extends React.Component<ILeftHandDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { leftHandEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            LeftHand [<b>{leftHandEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{leftHandEntity.name}</dd>
            <dt>
              <span id="effect">Effect</span>
            </dt>
            <dd>{leftHandEntity.effect}</dd>
            <dt>
              <span id="price">Price</span>
            </dt>
            <dd>{leftHandEntity.price}</dd>
            <dt>
              <span id="attack">Attack</span>
            </dt>
            <dd>{leftHandEntity.attack}</dd>
            <dt>
              <span id="defense">Defense</span>
            </dt>
            <dd>{leftHandEntity.defense}</dd>
            <dt>
              <span id="type">Type</span>
            </dt>
            <dd>{leftHandEntity.type}</dd>
            <dt>Shop</dt>
            <dd>
              {leftHandEntity.shops
                ? leftHandEntity.shops.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === leftHandEntity.shops.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/left-hand" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/left-hand/${leftHandEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ leftHand }: IRootState) => ({
  leftHandEntity: leftHand.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(LeftHandDetail);
