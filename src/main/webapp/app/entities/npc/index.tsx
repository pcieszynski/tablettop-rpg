import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Npc from './npc';
import NpcDetail from './npc-detail';
import NpcUpdate from './npc-update';
import NpcDeleteDialog from './npc-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NpcUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NpcUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NpcDetail} />
      <ErrorBoundaryRoute path={match.url} component={Npc} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={NpcDeleteDialog} />
  </>
);

export default Routes;
