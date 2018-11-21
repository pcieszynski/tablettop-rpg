import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './shop.reducer';
import { IShop } from 'app/shared/model/shop.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IShopDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ShopDetail extends React.Component<IShopDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { shopEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Shop [<b>{shopEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{shopEntity.name}</dd>
            <dt>
              <span id="isLoot">Is Loot</span>
            </dt>
            <dd>{shopEntity.isLoot ? 'true' : 'false'}</dd>
            <dt>Item</dt>
            <dd>
              {shopEntity.items
                ? shopEntity.items.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === shopEntity.items.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/shop" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/shop/${shopEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ shop }: IRootState) => ({
  shopEntity: shop.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ShopDetail);
