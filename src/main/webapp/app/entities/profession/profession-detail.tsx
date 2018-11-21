import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './profession.reducer';
import { IProfession } from 'app/shared/model/profession.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProfessionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ProfessionDetail extends React.Component<IProfessionDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { professionEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Profession [<b>{professionEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{professionEntity.name}</dd>
            <dt>
              <span id="strengthModifier">Strength Modifier</span>
            </dt>
            <dd>{professionEntity.strengthModifier}</dd>
            <dt>
              <span id="agilityModifier">Agility Modifier</span>
            </dt>
            <dd>{professionEntity.agilityModifier}</dd>
            <dt>
              <span id="constituitionModifier">Constituition Modifier</span>
            </dt>
            <dd>{professionEntity.constituitionModifier}</dd>
            <dt>
              <span id="intelligenceModifier">Intelligence Modifier</span>
            </dt>
            <dd>{professionEntity.intelligenceModifier}</dd>
            <dt>
              <span id="willpowerModifier">Willpower Modifier</span>
            </dt>
            <dd>{professionEntity.willpowerModifier}</dd>
            <dt>
              <span id="charismaModifier">Charisma Modifier</span>
            </dt>
            <dd>{professionEntity.charismaModifier}</dd>
            <dt>Skill</dt>
            <dd>
              {professionEntity.skills
                ? professionEntity.skills.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === professionEntity.skills.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/profession" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/profession/${professionEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ profession }: IRootState) => ({
  professionEntity: profession.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProfessionDetail);
