import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './boots.reducer';
import { IBoots } from 'app/shared/model/boots.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBootsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class BootsDetail extends React.Component<IBootsDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { bootsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Boots [<b>{bootsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{bootsEntity.name}</dd>
            <dt>
              <span id="effect">Effect</span>
            </dt>
            <dd>{bootsEntity.effect}</dd>
            <dt>
              <span id="price">Price</span>
            </dt>
            <dd>{bootsEntity.price}</dd>
            <dt>
              <span id="defence">Defence</span>
            </dt>
            <dd>{bootsEntity.defence}</dd>
            <dt>
              <span id="part">Part</span>
            </dt>
            <dd>{bootsEntity.part}</dd>
            <dt>Shop</dt>
            <dd>
              {bootsEntity.shops
                ? bootsEntity.shops.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === bootsEntity.shops.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/boots" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/boots/${bootsEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ boots }: IRootState) => ({
  bootsEntity: boots.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BootsDetail);
