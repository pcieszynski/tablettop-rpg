import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './gamemaster.reducer';
import { IGamemaster } from 'app/shared/model/gamemaster.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGamemasterDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class GamemasterDetail extends React.Component<IGamemasterDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { gamemasterEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Gamemaster [<b>{gamemasterEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>Player</dt>
            <dd>{gamemasterEntity.playerId ? gamemasterEntity.playerId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/gamemaster" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/gamemaster/${gamemasterEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ gamemaster }: IRootState) => ({
  gamemasterEntity: gamemaster.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(GamemasterDetail);
