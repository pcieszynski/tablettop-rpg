import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './monster.reducer';
import { IMonster } from 'app/shared/model/monster.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMonsterDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class MonsterDetail extends React.Component<IMonsterDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { monsterEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Monster [<b>{monsterEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="currentHP">Current HP</span>
            </dt>
            <dd>{monsterEntity.currentHP}</dd>
            <dt>Type</dt>
            <dd>{monsterEntity.typeId ? monsterEntity.typeId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/monster" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/monster/${monsterEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ monster }: IRootState) => ({
  monsterEntity: monster.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MonsterDetail);
