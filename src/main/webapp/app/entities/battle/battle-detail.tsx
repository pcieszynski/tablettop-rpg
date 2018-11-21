import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './battle.reducer';
import { IBattle } from 'app/shared/model/battle.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBattleDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class BattleDetail extends React.Component<IBattleDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { battleEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Battle [<b>{battleEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="monster">Monster</span>
            </dt>
            <dd>{battleEntity.monster}</dd>
            <dt>
              <span id="monsterNumber">Monster Number</span>
            </dt>
            <dd>{battleEntity.monsterNumber}</dd>
            <dt>Monster</dt>
            <dd>
              {battleEntity.monsters
                ? battleEntity.monsters.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === battleEntity.monsters.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
            <dt>Event</dt>
            <dd>{battleEntity.eventId ? battleEntity.eventId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/battle" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/battle/${battleEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ battle }: IRootState) => ({
  battleEntity: battle.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BattleDetail);
