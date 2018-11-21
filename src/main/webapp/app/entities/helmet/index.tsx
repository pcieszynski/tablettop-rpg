import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Helmet from './helmet';
import HelmetDetail from './helmet-detail';
import HelmetUpdate from './helmet-update';
import HelmetDeleteDialog from './helmet-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={HelmetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={HelmetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={HelmetDetail} />
      <ErrorBoundaryRoute path={match.url} component={Helmet} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={HelmetDeleteDialog} />
  </>
);

export default Routes;
