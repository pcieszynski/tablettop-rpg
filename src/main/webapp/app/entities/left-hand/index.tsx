import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import LeftHand from './left-hand';
import LeftHandDetail from './left-hand-detail';
import LeftHandUpdate from './left-hand-update';
import LeftHandDeleteDialog from './left-hand-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LeftHandUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LeftHandUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LeftHandDetail} />
      <ErrorBoundaryRoute path={match.url} component={LeftHand} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={LeftHandDeleteDialog} />
  </>
);

export default Routes;
