import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './legs.reducer';
import { ILegs } from 'app/shared/model/legs.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ILegsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class LegsDetail extends React.Component<ILegsDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { legsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Legs [<b>{legsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{legsEntity.name}</dd>
            <dt>
              <span id="effect">Effect</span>
            </dt>
            <dd>{legsEntity.effect}</dd>
            <dt>
              <span id="price">Price</span>
            </dt>
            <dd>{legsEntity.price}</dd>
            <dt>
              <span id="defence">Defence</span>
            </dt>
            <dd>{legsEntity.defence}</dd>
            <dt>
              <span id="part">Part</span>
            </dt>
            <dd>{legsEntity.part}</dd>
            <dt>Shop</dt>
            <dd>
              {legsEntity.shops
                ? legsEntity.shops.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === legsEntity.shops.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/legs" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/legs/${legsEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ legs }: IRootState) => ({
  legsEntity: legs.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(LegsDetail);
