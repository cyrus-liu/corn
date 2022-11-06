/**
 * 构造树型结构数据
 * @param {*} data 数据源
 * @param {*} id id字段 默认 'id'
 * @param {*} parentId 父节点字段 默认 'parentId'
 * @param {*} children 孩子节点字段 默认 'children'
 */
export function createTree(data, id, parentId, children) {
    const config = {
        id: id || 'id',
        parentId: parentId || 'parentId',
        childrenList: children || 'children'
    }

    const childrenListMap = {};
    const nodeIds = {};
    const tree = [];

    for (const d of data) {
        const parentId = d[config.parentId]
        if (childrenListMap[parentId] == null) {
            childrenListMap[parentId] = []
        }
        nodeIds[d[config.id]] = d
        childrenListMap[parentId].push(d)
    }

    for (const d of data) {
        const parentId = d[config.parentId]
        if (nodeIds[parentId] == null) {
            tree.push(d)
        }
    }

    for (const t of tree) {
        adaptToChildrenList(t)
    }

    function adaptToChildrenList(o) {
        if (childrenListMap[o[config.id]] !== null) {
            o[config.childrenList] = childrenListMap[o[config.id]]
        }
        if (o[config.childrenList]) {
            for (const c of o[config.childrenList]) {
                adaptToChildrenList(c)
            }
        }
    }

    return tree
}