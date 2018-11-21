import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Gloves from './gloves';
import GlovesDetail from './gloves-detail';
import GlovesUpdate from './gloves-update';
import GlovesDeleteDialog from './gloves-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GlovesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GlovesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GlovesDetail} />
      <ErrorBoundaryRoute path={match.url} component={Gloves} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={GlovesDeleteDialog} />
  </>
);

export default Routes;
