import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PlayerMessage from './player-message';
import PlayerMessageDetail from './player-message-detail';
import PlayerMessageUpdate from './player-message-update';
import PlayerMessageDeleteDialog from './player-message-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PlayerMessageUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PlayerMessageUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PlayerMessageDetail} />
      <ErrorBoundaryRoute path={match.url} component={PlayerMessage} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PlayerMessageDeleteDialog} />
  </>
);

export default Routes;
