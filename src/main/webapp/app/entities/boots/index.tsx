import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Boots from './boots';
import BootsDetail from './boots-detail';
import BootsUpdate from './boots-update';
import BootsDeleteDialog from './boots-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BootsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BootsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BootsDetail} />
      <ErrorBoundaryRoute path={match.url} component={Boots} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={BootsDeleteDialog} />
  </>
);

export default Routes;
