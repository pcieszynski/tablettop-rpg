import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Player from './player';
import Gamemaster from './gamemaster';
import Game from './game';
import Character from './character';
import Profession from './profession';
import Skill from './skill';
import Status from './status';
import Event from './event';
import PlayerMessage from './player-message';
import Battle from './battle';
import Monster from './monster';
import MonsterType from './monster-type';
import Npc from './npc';
import Shop from './shop';
import Item from './item';
import Armour from './armour';
import Boots from './boots';
import Legs from './legs';
import Helmet from './helmet';
import Gloves from './gloves';
import RightHand from './right-hand';
import LeftHand from './left-hand';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/player`} component={Player} />
      <ErrorBoundaryRoute path={`${match.url}/gamemaster`} component={Gamemaster} />
      <ErrorBoundaryRoute path={`${match.url}/game`} component={Game} />
      <ErrorBoundaryRoute path={`${match.url}/character`} component={Character} />
      <ErrorBoundaryRoute path={`${match.url}/profession`} component={Profession} />
      <ErrorBoundaryRoute path={`${match.url}/skill`} component={Skill} />
      <ErrorBoundaryRoute path={`${match.url}/status`} component={Status} />
      <ErrorBoundaryRoute path={`${match.url}/event`} component={Event} />
      <ErrorBoundaryRoute path={`${match.url}/player-message`} component={PlayerMessage} />
      <ErrorBoundaryRoute path={`${match.url}/battle`} component={Battle} />
      <ErrorBoundaryRoute path={`${match.url}/monster`} component={Monster} />
      <ErrorBoundaryRoute path={`${match.url}/monster-type`} component={MonsterType} />
      <ErrorBoundaryRoute path={`${match.url}/npc`} component={Npc} />
      <ErrorBoundaryRoute path={`${match.url}/shop`} component={Shop} />
      <ErrorBoundaryRoute path={`${match.url}/item`} component={Item} />
      <ErrorBoundaryRoute path={`${match.url}/armour`} component={Armour} />
      <ErrorBoundaryRoute path={`${match.url}/boots`} component={Boots} />
      <ErrorBoundaryRoute path={`${match.url}/legs`} component={Legs} />
      <ErrorBoundaryRoute path={`${match.url}/helmet`} component={Helmet} />
      <ErrorBoundaryRoute path={`${match.url}/gloves`} component={Gloves} />
      <ErrorBoundaryRoute path={`${match.url}/right-hand`} component={RightHand} />
      <ErrorBoundaryRoute path={`${match.url}/left-hand`} component={LeftHand} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
