import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Battle from './battle';
import BattleDetail from './battle-detail';
import BattleUpdate from './battle-update';
import BattleDeleteDialog from './battle-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BattleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BattleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BattleDetail} />
      <ErrorBoundaryRoute path={match.url} component={Battle} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={BattleDeleteDialog} />
  </>
);

export default Routes;
