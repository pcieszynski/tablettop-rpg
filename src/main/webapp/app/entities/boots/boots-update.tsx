import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IShop } from 'app/shared/model/shop.model';
import { getEntities as getShops } from 'app/entities/shop/shop.reducer';
import { getEntity, updateEntity, createEntity, reset } from './boots.reducer';
import { IBoots } from 'app/shared/model/boots.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBootsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IBootsUpdateState {
  isNew: boolean;
  idsshop: any[];
}

export class BootsUpdate extends React.Component<IBootsUpdateProps, IBootsUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsshop: [],
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getShops();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { bootsEntity } = this.props;
      const entity = {
        ...bootsEntity,
        ...values,
        shops: mapIdList(values.shops)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/boots');
  };

  render() {
    const { bootsEntity, shops, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="tabletTopRpgApp.boots.home.createOrEditLabel">Create or edit a Boots</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : bootsEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="boots-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    Name
                  </Label>
                  <AvField id="boots-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="effectLabel" for="effect">
                    Effect
                  </Label>
                  <AvField id="boots-effect" type="text" name="effect" />
                </AvGroup>
                <AvGroup>
                  <Label id="priceLabel" for="price">
                    Price
                  </Label>
                  <AvField id="boots-price" type="string" className="form-control" name="price" />
                </AvGroup>
                <AvGroup>
                  <Label id="defenceLabel" for="defence">
                    Defence
                  </Label>
                  <AvField id="boots-defence" type="string" className="form-control" name="defence" />
                </AvGroup>
                <AvGroup>
                  <Label id="partLabel" for="part">
                    Part
                  </Label>
                  <AvField id="boots-part" type="text" name="part" />
                </AvGroup>
                <AvGroup>
                  <Label for="shops">Shop</Label>
                  <AvInput
                    id="boots-shop"
                    type="select"
                    multiple
                    className="form-control"
                    name="shops"
                    value={bootsEntity.shops && bootsEntity.shops.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {shops
                      ? shops.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/boots" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  shops: storeState.shop.entities,
  bootsEntity: storeState.boots.entity,
  loading: storeState.boots.loading,
  updating: storeState.boots.updating,
  updateSuccess: storeState.boots.updateSuccess
});

const mapDispatchToProps = {
  getShops,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BootsUpdate);
