import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ISkill } from 'app/shared/model/skill.model';
import { getEntities as getSkills } from 'app/entities/skill/skill.reducer';
import { IGame } from 'app/shared/model/game.model';
import { getEntities as getGames } from 'app/entities/game/game.reducer';
import { IStatus } from 'app/shared/model/status.model';
import { getEntities as getStatuses } from 'app/entities/status/status.reducer';
import { IItem } from 'app/shared/model/item.model';
import { getEntities as getItems } from 'app/entities/item/item.reducer';
import { IProfession } from 'app/shared/model/profession.model';
import { getEntities as getProfessions } from 'app/entities/profession/profession.reducer';
import { IPlayer } from 'app/shared/model/player.model';
import { getEntities as getPlayers } from 'app/entities/player/player.reducer';
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
import { getEntity, updateEntity, createEntity, reset } from './character.reducer';
import { ICharacter } from 'app/shared/model/character.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICharacterUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ICharacterUpdateState {
  isNew: boolean;
  idsskill: any[];
  idsgame: any[];
  idsstatus: any[];
  idsitem: any[];
  professionId: string;
  playerId: string;
  helmetId: string;
  armourId: string;
  bootsId: string;
  glovesId: string;
  legsId: string;
  rightHandId: string;
  leftHandId: string;
}

export class CharacterUpdate extends React.Component<ICharacterUpdateProps, ICharacterUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsskill: [],
      idsgame: [],
      idsstatus: [],
      idsitem: [],
      professionId: '0',
      playerId: '0',
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

    this.props.getSkills();
    this.props.getGames();
    this.props.getStatuses();
    this.props.getItems();
    this.props.getProfessions();
    this.props.getPlayers();
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
      const { characterEntity } = this.props;
      const entity = {
        ...characterEntity,
        ...values,
        skills: mapIdList(values.skills),
        games: mapIdList(values.games),
        statuses: mapIdList(values.statuses),
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
    this.props.history.push('/entity/character');
  };

  render() {
    const {
      characterEntity,
      skills,
      games,
      statuses,
      items,
      professions,
      players,
      helmets,
      armours,
      boots,
      gloves,
      legs,
      rightHands,
      leftHands,
      loading,
      updating
    } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="tabletTopRpgApp.character.home.createOrEditLabel">Create or edit a Character</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : characterEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="character-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    Name
                  </Label>
                  <AvField id="character-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="levelLabel" for="level">
                    Level
                  </Label>
                  <AvField id="character-level" type="string" className="form-control" name="level" />
                </AvGroup>
                <AvGroup>
                  <Label id="experienceLabel" for="experience">
                    Experience
                  </Label>
                  <AvField id="character-experience" type="string" className="form-control" name="experience" />
                </AvGroup>
                <AvGroup>
                  <Label id="maxHitpointsLabel" for="maxHitpoints">
                    Max Hitpoints
                  </Label>
                  <AvField id="character-maxHitpoints" type="string" className="form-control" name="maxHitpoints" />
                </AvGroup>
                <AvGroup>
                  <Label id="currentHitpointsLabel" for="currentHitpoints">
                    Current Hitpoints
                  </Label>
                  <AvField id="character-currentHitpoints" type="string" className="form-control" name="currentHitpoints" />
                </AvGroup>
                <AvGroup>
                  <Label id="goldLabel" for="gold">
                    Gold
                  </Label>
                  <AvField id="character-gold" type="string" className="form-control" name="gold" />
                </AvGroup>
                <AvGroup>
                  <Label id="strengthLabel" for="strength">
                    Strength
                  </Label>
                  <AvField id="character-strength" type="string" className="form-control" name="strength" />
                </AvGroup>
                <AvGroup>
                  <Label id="agilityLabel" for="agility">
                    Agility
                  </Label>
                  <AvField id="character-agility" type="string" className="form-control" name="agility" />
                </AvGroup>
                <AvGroup>
                  <Label id="constituitionLabel" for="constituition">
                    Constituition
                  </Label>
                  <AvField id="character-constituition" type="string" className="form-control" name="constituition" />
                </AvGroup>
                <AvGroup>
                  <Label id="intelligenceLabel" for="intelligence">
                    Intelligence
                  </Label>
                  <AvField id="character-intelligence" type="string" className="form-control" name="intelligence" />
                </AvGroup>
                <AvGroup>
                  <Label id="willpowerLabel" for="willpower">
                    Willpower
                  </Label>
                  <AvField id="character-willpower" type="string" className="form-control" name="willpower" />
                </AvGroup>
                <AvGroup>
                  <Label id="charismaLabel" for="charisma">
                    Charisma
                  </Label>
                  <AvField id="character-charisma" type="string" className="form-control" name="charisma" />
                </AvGroup>
                <AvGroup>
                  <Label for="skills">Skill</Label>
                  <AvInput
                    id="character-skill"
                    type="select"
                    multiple
                    className="form-control"
                    name="skills"
                    value={characterEntity.skills && characterEntity.skills.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {skills
                      ? skills.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="games">Game</Label>
                  <AvInput
                    id="character-game"
                    type="select"
                    multiple
                    className="form-control"
                    name="games"
                    value={characterEntity.games && characterEntity.games.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {games
                      ? games.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="statuses">Status</Label>
                  <AvInput
                    id="character-status"
                    type="select"
                    multiple
                    className="form-control"
                    name="statuses"
                    value={characterEntity.statuses && characterEntity.statuses.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {statuses
                      ? statuses.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="items">Item</Label>
                  <AvInput
                    id="character-item"
                    type="select"
                    multiple
                    className="form-control"
                    name="items"
                    value={characterEntity.items && characterEntity.items.map(e => e.id)}
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
                <AvGroup>
                  <Label for="profession.id">Profession</Label>
                  <AvInput id="character-profession" type="select" className="form-control" name="professionId">
                    <option value="" key="0" />
                    {professions
                      ? professions.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="player.id">Player</Label>
                  <AvInput id="character-player" type="select" className="form-control" name="playerId">
                    <option value="" key="0" />
                    {players
                      ? players.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="helmet.id">Helmet</Label>
                  <AvInput id="character-helmet" type="select" className="form-control" name="helmetId">
                    <option value="" key="0" />
                    {helmets
                      ? helmets.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="armour.id">Armour</Label>
                  <AvInput id="character-armour" type="select" className="form-control" name="armourId">
                    <option value="" key="0" />
                    {armours
                      ? armours.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="boots.id">Boots</Label>
                  <AvInput id="character-boots" type="select" className="form-control" name="bootsId">
                    <option value="" key="0" />
                    {boots
                      ? boots.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="gloves.id">Gloves</Label>
                  <AvInput id="character-gloves" type="select" className="form-control" name="glovesId">
                    <option value="" key="0" />
                    {gloves
                      ? gloves.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="legs.id">Legs</Label>
                  <AvInput id="character-legs" type="select" className="form-control" name="legsId">
                    <option value="" key="0" />
                    {legs
                      ? legs.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="rightHand.id">Right Hand</Label>
                  <AvInput id="character-rightHand" type="select" className="form-control" name="rightHandId">
                    <option value="" key="0" />
                    {rightHands
                      ? rightHands.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="leftHand.id">Left Hand</Label>
                  <AvInput id="character-leftHand" type="select" className="form-control" name="leftHandId">
                    <option value="" key="0" />
                    {leftHands
                      ? leftHands.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/character" replace color="info">
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
  skills: storeState.skill.entities,
  games: storeState.game.entities,
  statuses: storeState.status.entities,
  items: storeState.item.entities,
  professions: storeState.profession.entities,
  players: storeState.player.entities,
  helmets: storeState.helmet.entities,
  armours: storeState.armour.entities,
  boots: storeState.boots.entities,
  gloves: storeState.gloves.entities,
  legs: storeState.legs.entities,
  rightHands: storeState.rightHand.entities,
  leftHands: storeState.leftHand.entities,
  characterEntity: storeState.character.entity,
  loading: storeState.character.loading,
  updating: storeState.character.updating,
  updateSuccess: storeState.character.updateSuccess
});

const mapDispatchToProps = {
  getSkills,
  getGames,
  getStatuses,
  getItems,
  getProfessions,
  getPlayers,
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
)(CharacterUpdate);
