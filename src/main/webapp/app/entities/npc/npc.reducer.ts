import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { INpc, defaultValue } from 'app/shared/model/npc.model';

export const ACTION_TYPES = {
  FETCH_NPC_LIST: 'npc/FETCH_NPC_LIST',
  FETCH_NPC: 'npc/FETCH_NPC',
  CREATE_NPC: 'npc/CREATE_NPC',
  UPDATE_NPC: 'npc/UPDATE_NPC',
  DELETE_NPC: 'npc/DELETE_NPC',
  RESET: 'npc/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<INpc>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type NpcState = Readonly<typeof initialState>;

// Reducer

export default (state: NpcState = initialState, action): NpcState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_NPC_LIST):
    case REQUEST(ACTION_TYPES.FETCH_NPC):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_NPC):
    case REQUEST(ACTION_TYPES.UPDATE_NPC):
    case REQUEST(ACTION_TYPES.DELETE_NPC):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_NPC_LIST):
    case FAILURE(ACTION_TYPES.FETCH_NPC):
    case FAILURE(ACTION_TYPES.CREATE_NPC):
    case FAILURE(ACTION_TYPES.UPDATE_NPC):
    case FAILURE(ACTION_TYPES.DELETE_NPC):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_NPC_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_NPC):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_NPC):
    case SUCCESS(ACTION_TYPES.UPDATE_NPC):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_NPC):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/npcs';

// Actions

export const getEntities: ICrudGetAllAction<INpc> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_NPC_LIST,
  payload: axios.get<INpc>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<INpc> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_NPC,
    payload: axios.get<INpc>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<INpc> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_NPC,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<INpc> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_NPC,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<INpc> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_NPC,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
