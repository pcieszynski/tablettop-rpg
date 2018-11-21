import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RightHand from './right-hand';
import RightHandDetail from './right-hand-detail';
import RightHandUpdate from './right-hand-update';
import RightHandDeleteDialog from './right-hand-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RightHandUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RightHandUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RightHandDetail} />
      <ErrorBoundaryRoute path={match.url} component={RightHand} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={RightHandDeleteDialog} />
  </>
);

export default Routes;
