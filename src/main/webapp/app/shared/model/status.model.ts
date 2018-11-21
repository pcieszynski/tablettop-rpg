import { ICharacter } from 'app/shared/model//character.model';

export interface IStatus {
  id?: number;
  name?: string;
  effect?: string;
  characters?: ICharacter[];
}

export const defaultValue: Readonly<IStatus> = {};
