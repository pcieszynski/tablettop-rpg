import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './gloves.reducer';
import { IGloves } from 'app/shared/model/gloves.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGlovesDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class GlovesDetail extends React.Component<IGlovesDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { glovesEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Gloves [<b>{glovesEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{glovesEntity.name}</dd>
            <dt>
              <span id="effect">Effect</span>
            </dt>
            <dd>{glovesEntity.effect}</dd>
            <dt>
              <span id="price">Price</span>
            </dt>
            <dd>{glovesEntity.price}</dd>
            <dt>
              <span id="defence">Defence</span>
            </dt>
            <dd>{glovesEntity.defence}</dd>
            <dt>
              <span id="part">Part</span>
            </dt>
            <dd>{glovesEntity.part}</dd>
            <dt>Shop</dt>
            <dd>
              {glovesEntity.shops
                ? glovesEntity.shops.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === glovesEntity.shops.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/gloves" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/gloves/${glovesEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ gloves }: IRootState) => ({
  glovesEntity: gloves.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(GlovesDetail);
