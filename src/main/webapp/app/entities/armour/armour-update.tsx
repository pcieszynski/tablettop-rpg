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
import { getEntity, updateEntity, createEntity, reset } from './armour.reducer';
import { IArmour } from 'app/shared/model/armour.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IArmourUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IArmourUpdateState {
  isNew: boolean;
  idsshop: any[];
}

export class ArmourUpdate extends React.Component<IArmourUpdateProps, IArmourUpdateState> {
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
      const { armourEntity } = this.props;
      const entity = {
        ...armourEntity,
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
    this.props.history.push('/entity/armour');
  };

  render() {
    const { armourEntity, shops, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="tabletTopRpgApp.armour.home.createOrEditLabel">Create or edit a Armour</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : armourEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="armour-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    Name
                  </Label>
                  <AvField id="armour-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="effectLabel" for="effect">
                    Effect
                  </Label>
                  <AvField id="armour-effect" type="text" name="effect" />
                </AvGroup>
                <AvGroup>
                  <Label id="priceLabel" for="price">
                    Price
                  </Label>
                  <AvField id="armour-price" type="string" className="form-control" name="price" />
                </AvGroup>
                <AvGroup>
                  <Label id="defenceLabel" for="defence">
                    Defence
                  </Label>
                  <AvField id="armour-defence" type="string" className="form-control" name="defence" />
                </AvGroup>
                <AvGroup>
                  <Label id="partLabel" for="part">
                    Part
                  </Label>
                  <AvField id="armour-part" type="text" name="part" />
                </AvGroup>
                <AvGroup>
                  <Label for="shops">Shop</Label>
                  <AvInput
                    id="armour-shop"
                    type="select"
                    multiple
                    className="form-control"
                    name="shops"
                    value={armourEntity.shops && armourEntity.shops.map(e => e.id)}
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
                <Button tag={Link} id="cancel-save" to="/entity/armour" replace color="info">
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
  armourEntity: storeState.armour.entity,
  loading: storeState.armour.loading,
  updating: storeState.armour.updating,
  updateSuccess: storeState.armour.updateSuccess
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
)(ArmourUpdate);
