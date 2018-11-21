import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MonsterType from './monster-type';
import MonsterTypeDetail from './monster-type-detail';
import MonsterTypeUpdate from './monster-type-update';
import MonsterTypeDeleteDialog from './monster-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MonsterTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MonsterTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MonsterTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={MonsterType} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MonsterTypeDeleteDialog} />
  </>
);

export default Routes;
