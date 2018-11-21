import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './player-message.reducer';
import { IPlayerMessage } from 'app/shared/model/player-message.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPlayerMessageDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PlayerMessageDetail extends React.Component<IPlayerMessageDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { playerMessageEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            PlayerMessage [<b>{playerMessageEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="message">Message</span>
            </dt>
            <dd>{playerMessageEntity.message}</dd>
            <dt>
              <span id="attack">Attack</span>
            </dt>
            <dd>{playerMessageEntity.attack}</dd>
            <dt>
              <span id="heal">Heal</span>
            </dt>
            <dd>{playerMessageEntity.heal}</dd>
            <dt>
              <span id="difficulty">Difficulty</span>
            </dt>
            <dd>{playerMessageEntity.difficulty}</dd>
            <dt>
              <span id="success">Success</span>
            </dt>
            <dd>{playerMessageEntity.success ? 'true' : 'false'}</dd>
            <dt>
              <span id="attribute">Attribute</span>
            </dt>
            <dd>{playerMessageEntity.attribute}</dd>
            <dt>Player</dt>
            <dd>{playerMessageEntity.playerId ? playerMessageEntity.playerId : ''}</dd>
            <dt>Event</dt>
            <dd>{playerMessageEntity.eventId ? playerMessageEntity.eventId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/player-message" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/player-message/${playerMessageEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ playerMessage }: IRootState) => ({
  playerMessageEntity: playerMessage.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PlayerMessageDetail);
