import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Armour from './armour';
import ArmourDetail from './armour-detail';
import ArmourUpdate from './armour-update';
import ArmourDeleteDialog from './armour-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ArmourUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ArmourUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ArmourDetail} />
      <ErrorBoundaryRoute path={match.url} component={Armour} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ArmourDeleteDialog} />
  </>
);

export default Routes;
