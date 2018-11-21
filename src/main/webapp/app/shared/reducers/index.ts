import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import player, {
  PlayerState
} from 'app/entities/player/player.reducer';
// prettier-ignore
import gamemaster, {
  GamemasterState
} from 'app/entities/gamemaster/gamemaster.reducer';
// prettier-ignore
import game, {
  GameState
} from 'app/entities/game/game.reducer';
// prettier-ignore
import character, {
  CharacterState
} from 'app/entities/character/character.reducer';
// prettier-ignore
import profession, {
  ProfessionState
} from 'app/entities/profession/profession.reducer';
// prettier-ignore
import skill, {
  SkillState
} from 'app/entities/skill/skill.reducer';
// prettier-ignore
import status, {
  StatusState
} from 'app/entities/status/status.reducer';
// prettier-ignore
import event, {
  EventState
} from 'app/entities/event/event.reducer';
// prettier-ignore
import playerMessage, {
  PlayerMessageState
} from 'app/entities/player-message/player-message.reducer';
// prettier-ignore
import battle, {
  BattleState
} from 'app/entities/battle/battle.reducer';
// prettier-ignore
import monster, {
  MonsterState
} from 'app/entities/monster/monster.reducer';
// prettier-ignore
import monsterType, {
  MonsterTypeState
} from 'app/entities/monster-type/monster-type.reducer';
// prettier-ignore
import npc, {
  NpcState
} from 'app/entities/npc/npc.reducer';
// prettier-ignore
import shop, {
  ShopState
} from 'app/entities/shop/shop.reducer';
// prettier-ignore
import item, {
  ItemState
} from 'app/entities/item/item.reducer';
// prettier-ignore
import armour, {
  ArmourState
} from 'app/entities/armour/armour.reducer';
// prettier-ignore
import boots, {
  BootsState
} from 'app/entities/boots/boots.reducer';
// prettier-ignore
import legs, {
  LegsState
} from 'app/entities/legs/legs.reducer';
// prettier-ignore
import helmet, {
  HelmetState
} from 'app/entities/helmet/helmet.reducer';
// prettier-ignore
import gloves, {
  GlovesState
} from 'app/entities/gloves/gloves.reducer';
// prettier-ignore
import rightHand, {
  RightHandState
} from 'app/entities/right-hand/right-hand.reducer';
// prettier-ignore
import leftHand, {
  LeftHandState
} from 'app/entities/left-hand/left-hand.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly player: PlayerState;
  readonly gamemaster: GamemasterState;
  readonly game: GameState;
  readonly character: CharacterState;
  readonly profession: ProfessionState;
  readonly skill: SkillState;
  readonly status: StatusState;
  readonly event: EventState;
  readonly playerMessage: PlayerMessageState;
  readonly battle: BattleState;
  readonly monster: MonsterState;
  readonly monsterType: MonsterTypeState;
  readonly npc: NpcState;
  readonly shop: ShopState;
  readonly item: ItemState;
  readonly armour: ArmourState;
  readonly boots: BootsState;
  readonly legs: LegsState;
  readonly helmet: HelmetState;
  readonly gloves: GlovesState;
  readonly rightHand: RightHandState;
  readonly leftHand: LeftHandState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  player,
  gamemaster,
  game,
  character,
  profession,
  skill,
  status,
  event,
  playerMessage,
  battle,
  monster,
  monsterType,
  npc,
  shop,
  item,
  armour,
  boots,
  legs,
  helmet,
  gloves,
  rightHand,
  leftHand,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
