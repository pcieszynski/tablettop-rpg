import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './skill.reducer';
import { ISkill } from 'app/shared/model/skill.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISkillDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class SkillDetail extends React.Component<ISkillDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { skillEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Skill [<b>{skillEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{skillEntity.name}</dd>
            <dt>
              <span id="description">Description</span>
            </dt>
            <dd>{skillEntity.description}</dd>
            <dt>
              <span id="damage">Damage</span>
            </dt>
            <dd>{skillEntity.damage}</dd>
            <dt>
              <span id="heal">Heal</span>
            </dt>
            <dd>{skillEntity.heal}</dd>
            <dt>
              <span id="level">Level</span>
            </dt>
            <dd>{skillEntity.level}</dd>
          </dl>
          <Button tag={Link} to="/entity/skill" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/skill/${skillEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ skill }: IRootState) => ({
  skillEntity: skill.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(SkillDetail);
