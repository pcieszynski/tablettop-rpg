import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Gamemaster from './gamemaster';
import GamemasterDetail from './gamemaster-detail';
import GamemasterUpdate from './gamemaster-update';
import GamemasterDeleteDialog from './gamemaster-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GamemasterUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GamemasterUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GamemasterDetail} />
      <ErrorBoundaryRoute path={match.url} component={Gamemaster} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={GamemasterDeleteDialog} />
  </>
);

export default Routes;
