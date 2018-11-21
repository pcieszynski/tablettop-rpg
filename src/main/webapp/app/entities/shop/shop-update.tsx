import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IItem } from 'app/shared/model/item.model';
import { getEntities as getItems } from 'app/entities/item/item.reducer';
import { IHelmet } from 'app/shared/model/helmet.model';
import { getEntities as getHelmets } from 'app/entities/helmet/helmet.reducer';
import { IArmour } from 'app/shared/model/armour.model';
import { getEntities as getArmours } from 'app/entities/armour/armour.reducer';
import { IBoots } from 'app/shared/model/boots.model';
import { getEntities as getBoots } from 'app/entities/boots/boots.reducer';
import { IGloves } from 'app/shared/model/gloves.model';
import { getEntities as getGloves } from 'app/entities/gloves/gloves.reducer';
import { ILegs } from 'app/shared/model/legs.model';
import { getEntities as getLegs } from 'app/entities/legs/legs.reducer';
import { IRightHand } from 'app/shared/model/right-hand.model';
import { getEntities as getRightHands } from 'app/entities/right-hand/right-hand.reducer';
import { ILeftHand } from 'app/shared/model/left-hand.model';
import { getEntities as getLeftHands } from 'app/entities/left-hand/left-hand.reducer';
import { getEntity, updateEntity, createEntity, reset } from './shop.reducer';
import { IShop } from 'app/shared/model/shop.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IShopUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IShopUpdateState {
  isNew: boolean;
  idsitem: any[];
  helmetId: string;
  armourId: string;
  bootsId: string;
  glovesId: string;
  legsId: string;
  rightHandId: string;
  leftHandId: string;
}

export class ShopUpdate extends React.Component<IShopUpdateProps, IShopUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsitem: [],
      helmetId: '0',
      armourId: '0',
      bootsId: '0',
      glovesId: '0',
      legsId: '0',
      rightHandId: '0',
      leftHandId: '0',
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

    this.props.getItems();
    this.props.getHelmets();
    this.props.getArmours();
    this.props.getBoots();
    this.props.getGloves();
    this.props.getLegs();
    this.props.getRightHands();
    this.props.getLeftHands();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { shopEntity } = this.props;
      const entity = {
        ...shopEntity,
        ...values,
        items: mapIdList(values.items)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/shop');
  };

  render() {
    const { shopEntity, items, helmets, armours, boots, gloves, legs, rightHands, leftHands, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="tabletTopRpgApp.shop.home.createOrEditLabel">Create or edit a Shop</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : shopEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="shop-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    Name
                  </Label>
                  <AvField id="shop-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="isLootLabel" check>
                    <AvInput id="shop-isLoot" type="checkbox" className="form-control" name="isLoot" />
                    Is Loot
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label for="items">Item</Label>
                  <AvInput
                    id="shop-item"
                    type="select"
                    multiple
                    className="form-control"
                    name="items"
                    value={shopEntity.items && shopEntity.items.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {items
                      ? items.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/shop" replace color="info">
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
  items: storeState.item.entities,
  helmets: storeState.helmet.entities,
  armours: storeState.armour.entities,
  boots: storeState.boots.entities,
  gloves: storeState.gloves.entities,
  legs: storeState.legs.entities,
  rightHands: storeState.rightHand.entities,
  leftHands: storeState.leftHand.entities,
  shopEntity: storeState.shop.entity,
  loading: storeState.shop.loading,
  updating: storeState.shop.updating,
  updateSuccess: storeState.shop.updateSuccess
});

const mapDispatchToProps = {
  getItems,
  getHelmets,
  getArmours,
  getBoots,
  getGloves,
  getLegs,
  getRightHands,
  getLeftHands,
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
)(ShopUpdate);
