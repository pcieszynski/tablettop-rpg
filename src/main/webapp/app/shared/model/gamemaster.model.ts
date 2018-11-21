import { IGame } from 'app/shared/model//game.model';

export interface IGamemaster {
  id?: number;
  games?: IGame[];
  playerId?: number;
}

export const defaultValue: Readonly<IGamemaster> = {};
