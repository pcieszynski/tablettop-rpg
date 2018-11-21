import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Legs from './legs';
import LegsDetail from './legs-detail';
import LegsUpdate from './legs-update';
import LegsDeleteDialog from './legs-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LegsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LegsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LegsDetail} />
      <ErrorBoundaryRoute path={match.url} component={Legs} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={LegsDeleteDialog} />
  </>
);

export default Routes;
