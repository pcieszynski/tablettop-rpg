import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Monster from './monster';
import MonsterDetail from './monster-detail';
import MonsterUpdate from './monster-update';
import MonsterDeleteDialog from './monster-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MonsterUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MonsterUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MonsterDetail} />
      <ErrorBoundaryRoute path={match.url} component={Monster} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MonsterDeleteDialog} />
  </>
);

export default Routes;
