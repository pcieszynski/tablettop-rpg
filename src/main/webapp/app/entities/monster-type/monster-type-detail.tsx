import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './monster-type.reducer';
import { IMonsterType } from 'app/shared/model/monster-type.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMonsterTypeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class MonsterTypeDetail extends React.Component<IMonsterTypeDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { monsterTypeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            MonsterType [<b>{monsterTypeEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{monsterTypeEntity.name}</dd>
            <dt>
              <span id="strength">Strength</span>
            </dt>
            <dd>{monsterTypeEntity.strength}</dd>
            <dt>
              <span id="agility">Agility</span>
            </dt>
            <dd>{monsterTypeEntity.agility}</dd>
            <dt>
              <span id="constituition">Constituition</span>
            </dt>
            <dd>{monsterTypeEntity.constituition}</dd>
            <dt>
              <span id="intelligence">Intelligence</span>
            </dt>
            <dd>{monsterTypeEntity.intelligence}</dd>
            <dt>
              <span id="willpower">Willpower</span>
            </dt>
            <dd>{monsterTypeEntity.willpower}</dd>
            <dt>
              <span id="charisma">Charisma</span>
            </dt>
            <dd>{monsterTypeEntity.charisma}</dd>
            <dt>
              <span id="hitpoints">Hitpoints</span>
            </dt>
            <dd>{monsterTypeEntity.hitpoints}</dd>
            <dt>
              <span id="attack">Attack</span>
            </dt>
            <dd>{monsterTypeEntity.attack}</dd>
            <dt>
              <span id="defense">Defense</span>
            </dt>
            <dd>{monsterTypeEntity.defense}</dd>
          </dl>
          <Button tag={Link} to="/entity/monster-type" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/monster-type/${monsterTypeEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ monsterType }: IRootState) => ({
  monsterTypeEntity: monsterType.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MonsterTypeDetail);
