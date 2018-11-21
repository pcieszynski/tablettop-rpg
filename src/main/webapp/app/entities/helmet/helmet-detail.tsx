import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './helmet.reducer';
import { IHelmet } from 'app/shared/model/helmet.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IHelmetDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class HelmetDetail extends React.Component<IHelmetDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { helmetEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Helmet [<b>{helmetEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{helmetEntity.name}</dd>
            <dt>
              <span id="effect">Effect</span>
            </dt>
            <dd>{helmetEntity.effect}</dd>
            <dt>
              <span id="price">Price</span>
            </dt>
            <dd>{helmetEntity.price}</dd>
            <dt>
              <span id="defence">Defence</span>
            </dt>
            <dd>{helmetEntity.defence}</dd>
            <dt>
              <span id="part">Part</span>
            </dt>
            <dd>{helmetEntity.part}</dd>
            <dt>Shop</dt>
            <dd>
              {helmetEntity.shops
                ? helmetEntity.shops.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === helmetEntity.shops.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/helmet" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/helmet/${helmetEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ helmet }: IRootState) => ({
  helmetEntity: helmet.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(HelmetDetail);
