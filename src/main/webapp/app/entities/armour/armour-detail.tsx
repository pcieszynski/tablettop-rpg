import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './armour.reducer';
import { IArmour } from 'app/shared/model/armour.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IArmourDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ArmourDetail extends React.Component<IArmourDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { armourEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Armour [<b>{armourEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{armourEntity.name}</dd>
            <dt>
              <span id="effect">Effect</span>
            </dt>
            <dd>{armourEntity.effect}</dd>
            <dt>
              <span id="price">Price</span>
            </dt>
            <dd>{armourEntity.price}</dd>
            <dt>
              <span id="defence">Defence</span>
            </dt>
            <dd>{armourEntity.defence}</dd>
            <dt>
              <span id="part">Part</span>
            </dt>
            <dd>{armourEntity.part}</dd>
            <dt>Shop</dt>
            <dd>
              {armourEntity.shops
                ? armourEntity.shops.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === armourEntity.shops.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/armour" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/armour/${armourEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ armour }: IRootState) => ({
  armourEntity: armour.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ArmourDetail);
