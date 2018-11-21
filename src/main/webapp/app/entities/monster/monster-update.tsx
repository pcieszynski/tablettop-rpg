import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IMonsterType } from 'app/shared/model/monster-type.model';
import { getEntities as getMonsterTypes } from 'app/entities/monster-type/monster-type.reducer';
import { IBattle } from 'app/shared/model/battle.model';
import { getEntities as getBattles } from 'app/entities/battle/battle.reducer';
import { getEntity, updateEntity, createEntity, reset } from './monster.reducer';
import { IMonster } from 'app/shared/model/monster.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMonsterUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IMonsterUpdateState {
  isNew: boolean;
  typeId: string;
  battleId: string;
}

export class MonsterUpdate extends React.Component<IMonsterUpdateProps, IMonsterUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      typeId: '0',
      battleId: '0',
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

    this.props.getMonsterTypes();
    this.props.getBattles();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { monsterEntity } = this.props;
      const entity = {
        ...monsterEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/monster');
  };

  render() {
    const { monsterEntity, monsterTypes, battles, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="tabletTopRpgApp.monster.home.createOrEditLabel">Create or edit a Monster</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : monsterEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="monster-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="currentHPLabel" for="currentHP">
                    Current HP
                  </Label>
                  <AvField id="monster-currentHP" type="string" className="form-control" name="currentHP" />
                </AvGroup>
                <AvGroup>
                  <Label for="type.id">Type</Label>
                  <AvInput id="monster-type" type="select" className="form-control" name="typeId">
                    <option value="" key="0" />
                    {monsterTypes
                      ? monsterTypes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/monster" replace color="info">
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
  monsterTypes: storeState.monsterType.entities,
  battles: storeState.battle.entities,
  monsterEntity: storeState.monster.entity,
  loading: storeState.monster.loading,
  updating: storeState.monster.updating,
  updateSuccess: storeState.monster.updateSuccess
});

const mapDispatchToProps = {
  getMonsterTypes,
  getBattles,
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
)(MonsterUpdate);
