import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './character.reducer';
import { ICharacter } from 'app/shared/model/character.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICharacterDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class CharacterDetail extends React.Component<ICharacterDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { characterEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Character [<b>{characterEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{characterEntity.name}</dd>
            <dt>
              <span id="level">Level</span>
            </dt>
            <dd>{characterEntity.level}</dd>
            <dt>
              <span id="experience">Experience</span>
            </dt>
            <dd>{characterEntity.experience}</dd>
            <dt>
              <span id="maxHitpoints">Max Hitpoints</span>
            </dt>
            <dd>{characterEntity.maxHitpoints}</dd>
            <dt>
              <span id="currentHitpoints">Current Hitpoints</span>
            </dt>
            <dd>{characterEntity.currentHitpoints}</dd>
            <dt>
              <span id="gold">Gold</span>
            </dt>
            <dd>{characterEntity.gold}</dd>
            <dt>
              <span id="strength">Strength</span>
            </dt>
            <dd>{characterEntity.strength}</dd>
            <dt>
              <span id="agility">Agility</span>
            </dt>
            <dd>{characterEntity.agility}</dd>
            <dt>
              <span id="constituition">Constituition</span>
            </dt>
            <dd>{characterEntity.constituition}</dd>
            <dt>
              <span id="intelligence">Intelligence</span>
            </dt>
            <dd>{characterEntity.intelligence}</dd>
            <dt>
              <span id="willpower">Willpower</span>
            </dt>
            <dd>{characterEntity.willpower}</dd>
            <dt>
              <span id="charisma">Charisma</span>
            </dt>
            <dd>{characterEntity.charisma}</dd>
            <dt>Skill</dt>
            <dd>
              {characterEntity.skills
                ? characterEntity.skills.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === characterEntity.skills.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
            <dt>Game</dt>
            <dd>
              {characterEntity.games
                ? characterEntity.games.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === characterEntity.games.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
            <dt>Status</dt>
            <dd>
              {characterEntity.statuses
                ? characterEntity.statuses.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === characterEntity.statuses.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
            <dt>Item</dt>
            <dd>
              {characterEntity.items
                ? characterEntity.items.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === characterEntity.items.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
            <dt>Profession</dt>
            <dd>{characterEntity.professionId ? characterEntity.professionId : ''}</dd>
            <dt>Player</dt>
            <dd>{characterEntity.playerId ? characterEntity.playerId : ''}</dd>
            <dt>Helmet</dt>
            <dd>{characterEntity.helmetId ? characterEntity.helmetId : ''}</dd>
            <dt>Armour</dt>
            <dd>{characterEntity.armourId ? characterEntity.armourId : ''}</dd>
            <dt>Boots</dt>
            <dd>{characterEntity.bootsId ? characterEntity.bootsId : ''}</dd>
            <dt>Gloves</dt>
            <dd>{characterEntity.glovesId ? characterEntity.glovesId : ''}</dd>
            <dt>Legs</dt>
            <dd>{characterEntity.legsId ? characterEntity.legsId : ''}</dd>
            <dt>Right Hand</dt>
            <dd>{characterEntity.rightHandId ? characterEntity.rightHandId : ''}</dd>
            <dt>Left Hand</dt>
            <dd>{characterEntity.leftHandId ? characterEntity.leftHandId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/character" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/character/${characterEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ character }: IRootState) => ({
  characterEntity: character.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CharacterDetail);
