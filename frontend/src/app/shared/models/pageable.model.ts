import { Sort } from './sort.model';
export class Pageable {
    sort?: Sort = new Sort();
    offset?: number;
    pageNumber?: number;
    pageSize?: number;
    paged?: number;
    unpaged?: number;
}
