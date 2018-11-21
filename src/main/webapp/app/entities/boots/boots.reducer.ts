import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBoots, defaultValue } from 'app/shared/model/boots.model';

export const ACTION_TYPES = {
  FETCH_BOOTS_LIST: 'boots/FETCH_BOOTS_LIST',
  FETCH_BOOTS: 'boots/FETCH_BOOTS',
  CREATE_BOOTS: 'boots/CREATE_BOOTS',
  UPDATE_BOOTS: 'boots/UPDATE_BOOTS',
  DELETE_BOOTS: 'boots/DELETE_BOOTS',
  RESET: 'boots/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBoots>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type BootsState = Readonly<typeof initialState>;

// Reducer

export default (state: BootsState = initialState, action): BootsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BOOTS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BOOTS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BOOTS):
    case REQUEST(ACTION_TYPES.UPDATE_BOOTS):
    case REQUEST(ACTION_TYPES.DELETE_BOOTS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_BOOTS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BOOTS):
    case FAILURE(ACTION_TYPES.CREATE_BOOTS):
    case FAILURE(ACTION_TYPES.UPDATE_BOOTS):
    case FAILURE(ACTION_TYPES.DELETE_BOOTS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_BOOTS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_BOOTS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BOOTS):
    case SUCCESS(ACTION_TYPES.UPDATE_BOOTS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BOOTS):
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

const apiUrl = 'api/boots';

// Actions

export const getEntities: ICrudGetAllAction<IBoots> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_BOOTS_LIST,
  payload: axios.get<IBoots>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IBoots> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BOOTS,
    payload: axios.get<IBoots>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBoots> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BOOTS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBoots> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BOOTS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBoots> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BOOTS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
