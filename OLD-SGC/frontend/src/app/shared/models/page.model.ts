import { Pageable } from "./pageable.model";
import { Sort } from "./sort.model";

export class Page<T> {
    content: T[] = [];
    pageable: Pageable = new Pageable();
    totalPages?: number;
    totalElements?: number;
    last?: boolean;
    size?: number;
    number: number = 0;
    sort?: Sort = new Sort();
    numberOfElements?: number;
    first?: number;
    empty?: number;
}
